package com.hooniegit.KafkaProducer;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.hooniegit.KafkaProducer.Data.Inner;
import com.hooniegit.KafkaProducer.Data.Outer;
import com.hooniegit.KafkaProducer.Data.StateCondition;
import com.hooniegit.KafkaProducer.Serializer.ByteSerialization;

@SpringBootApplication
public class KafkaProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaProducerApplication.class, args);

		workspace();

	}

	private static void workspace() {

		ByteSerialization bs = new ByteSerialization();
		Random random = new Random();

		// Test Repeat
		for (int rep = 1; rep <= 15; rep++) {

			LocalDateTime st = LocalDateTime.now();
			for (int i = 1; i <= 6000; i++) {

				for (int j = 1; j <= 10; j++) {

					List<Inner> il = new ArrayList<>();
					int toolId = j + (i - 1) * 10;

					for (int k = 1; k <= 30; k++) {
						int parameter = k + (j - 1) * 30 + (i - 1) * 300;
						il.add(new Inner(toolId, StateCondition.UNKNOWN, null, null, parameter, random.nextInt(), true));
					}

					Outer<List<Inner>> outer = new Outer<List<Inner>>(il);
					try {
						byte[] ob = bs.<Outer<List<Inner>>>serializeToBytes(outer);
					} catch (Exception ex) {
						ex.printStackTrace();
					}

				}

			}
			
			LocalDateTime ed = LocalDateTime.now();
			System.out.println(Duration.between(st, ed));

		}

	}

}
