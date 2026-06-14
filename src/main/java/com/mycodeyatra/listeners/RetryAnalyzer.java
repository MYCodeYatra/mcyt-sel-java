package com.mycodeyatra.listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * RetryAnalyzer implements IRetryAnalyzer to automatically
 * re-run flaky/failed tests up to MAX_RETRY_COUNT times before
 * marking them as a true failure.
 */
public class RetryAnalyzer implements IRetryAnalyzer {

    private int retryCount = 0;
    private static final int MAX_RETRY_COUNT = 2;

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < MAX_RETRY_COUNT) {
            retryCount++;
            System.out.println("[RetryAnalyzer] Retrying test: " + result.getName()
                    + " | Attempt: " + retryCount + " of " + MAX_RETRY_COUNT);
            return true;
        }
        return false;
    }
}
