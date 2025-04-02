package com.hooniegit.Xtream.Stream;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;

import java.util.List;

/**
 * LMAX Disruptor 기반의 이벤트를 발행하는 클래스입니다. 이벤트 처리 시 동작할 커스텀 핸들러 클래스들을 리스트로 입력 받습니다.
 */

public class Stream<T> {
    private final Disruptor<Event<T>> disruptor;
    private final RingBuffer<Event<T>> ringBuffer;

    public Stream(List<Handler<T>> handlers) {

        disruptor = new Disruptor<>(
                Event::new,
                1024,
                DaemonThreadFactory.INSTANCE,
                ProducerType.SINGLE,
                new SleepingWaitStrategy()
        );

        if (handlers.size() > 1) {
            disruptor.handleEventsWith(handlers.get(0));
            for (int i = 1; i < handlers.size(); i++) {
            	if (i != handlers.size() - 1) {
            		disruptor.after(handlers.get(i - 1)).handleEventsWith(handlers.get(i));
            	} else {
            		disruptor.after(handlers.get(i - 1)).handleEventsWith(handlers.get(i))
            			.then(new ClearingEventHandler<T>());
            	}
            }
        } else {
            disruptor.handleEventsWith(handlers.get(0));
        }

        disruptor.start();
        ringBuffer = disruptor.getRingBuffer();
    }

    /**
     * 입력받은 데이터를 기반으로 이벤트를 발행합니다.
     * @param initialData
     */
    public void publishInitialEvent(T initialData) {
        long sequence = ringBuffer.next();
        try {
            Event<T> event = ringBuffer.get(sequence);
            event.setData(initialData);
        } finally {
            ringBuffer.publish(sequence);
        }
    }

    /**
     * LMAX Disruptor 스레드를 정지 시킵니다.
     * @throws Exception
     */
    public void stop() throws Exception {
        disruptor.shutdown();
    }
}
