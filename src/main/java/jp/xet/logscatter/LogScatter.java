/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jp.xet.logscatter;

import java.text.DecimalFormat;
import java.util.stream.LongStream;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class LogScatter implements CommandLineRunner {
	
	private final TaskExecutor taskExecutor;
	
	@Override
	public void run(String... args) throws Exception {
		DecimalFormat formatter = new DecimalFormat("000,000,000,000,000,000,000");
		taskExecutor.execute(() -> LongStream.iterate(0, v -> v + 1)
			.peek(this::sleep)
			.mapToObj(formatter::format)
			.forEach(log::info));
	}
	
	private void sleep(long v) {
		if (v % 5 == 0) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				log.error("Unexpected interrupt", e);
			}
		}
	}
}
