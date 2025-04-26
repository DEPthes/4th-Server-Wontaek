package taek.servlet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import taek.servlet.thread.HelloRunnable;
import taek.servlet.thread.HelloThread;

import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

import static taek.servlet.thread.util.ThreadUtil.sleep;
import static taek.servlet.thread.util.MyLogger.log;


@SpringBootApplication
public class ServletApplication {

	public static void main(String[] args) {
		MyTask task = new MyTask();
		Thread t = new Thread(task, "work");
		t.start();
		sleep(1000);
		task.flag = false;
		log("flag = " + task.flag + ", count = " + task.count + " in main");
	}


	static class MyTask implements Runnable {
		 boolean flag = true;
		 long count;
		//volatile boolean flag = true;
		//volatile long count;
		@Override
		public void run() {
			while (flag) {
				count++;
				//1억번에 한번씩 출력
				if (count % 100_000_000 == 0) {
					//주석 처리 한다면...
					log("flag = " + flag + ", count = " + count + " in while()");
				}
			}
			log("flag = " + flag + ", count = " + count + " 종료"); }

	}
}







