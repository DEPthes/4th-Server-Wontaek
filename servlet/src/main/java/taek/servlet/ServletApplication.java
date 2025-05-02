package taek.servlet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import taek.servlet.thread.*;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.LockSupport;

import static taek.servlet.thread.util.ExecutorUtils.printState;
import static taek.servlet.thread.util.ThreadUtil.sleep;
import static taek.servlet.thread.util.MyLogger.log;


@SpringBootApplication
public class ServletApplication {

//	public static void main(String[] args) {
//		ExecutorService es = Executors.newFixedThreadPool(2);
//		//ExecutorService es = new ThreadPoolExecutor(2, 2, 0L,TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
//		log("pool 생성"); printState(es);
//		for (int i = 1; i <= 6; i++) {
//			String taskName = "task" + i;
//			es.execute(new RunnableTask(taskName));
//			printState(es, taskName);
//		}
//		es.shutdown();
//		log("== shutdown 완료 ==");
//	}

//	public static void main(String[] args) throws InterruptedException {
//		//ExecutorService es = Executors.newCachedThreadPool();
//		//keepAliveTime 60초 -> 3초로 조절
//		ThreadPoolExecutor es = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 3,
//				TimeUnit.SECONDS, new SynchronousQueue<>()); log("pool 생성");
//		printState(es);
//		for (int i = 1; i <= 4; i++) {
//			String taskName = "task" + i;
//			es.execute(new RunnableTask(taskName));
//			printState(es, taskName);
//		}
//		sleep(3000);
//		log("== 작업 수행 완료 =="); printState(es);
//		sleep(3000);
//		log("== maximumPoolSize 대기 시간 초과 ==");
//		printState(es);
//		es.shutdown();
//		log("== shutdown 완료 ==");
//		printState(es);
//	}

//	static final int TASK_SIZE = 1100; // 1. 일반
	 static final int TASK_SIZE = 1200; // 2. 긴급
	// static final int TASK_SIZE = 1201; // 3. 거절
	public static void main(String[] args) throws InterruptedException {
		ExecutorService es = new ThreadPoolExecutor(100, 200, 60,
				TimeUnit.SECONDS, new ArrayBlockingQueue<>(1000));
		printState(es);
		long startMs = System.currentTimeMillis();
		for (int i = 1; i <= TASK_SIZE; i++) {
			String taskName = "task" + i;
			try {
				es.execute(new RunnableTask(taskName));
				printState(es, taskName);
			} catch (RejectedExecutionException e) {
				log(taskName + " -> " + e);
			}
		}
		es.shutdown();
		long endMs = System.currentTimeMillis();
		log("time: " + (endMs - startMs));
	}

}







