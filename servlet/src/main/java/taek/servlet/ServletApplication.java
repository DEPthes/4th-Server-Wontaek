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

	public static void main(String[] args) {
		SpringApplication.run(ServletApplication.class, args);
	}

}







