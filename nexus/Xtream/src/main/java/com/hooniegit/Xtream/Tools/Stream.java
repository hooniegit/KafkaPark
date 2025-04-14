package com.hooniegit.Xtream.Tools;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;

import java.util.List;

/**
 * LMAX Stream Class for Event Processing
 * @param <T>
 */
public class Stream<T> {

    private final Disruptor<Event<T>> disruptor;
    private final RingBuffer<Event<T>> ringBuffer;

    public Stream(List<Handler<T>> handlers, int bufferMultiplier) {
        disruptor = new Disruptor<>(
                Event::new,
                1024 * bufferMultiplier,
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
     * Publish Event
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
     * Stop Disruptor Thread
     * @throws Exception
     */
    public void stop() throws Exception {
        disruptor.shutdown();
    }

}
