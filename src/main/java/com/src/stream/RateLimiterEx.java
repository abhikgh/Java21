package com.src.stream;

import java.util.HashMap;
import java.util.PriorityQueue;

public class RateLimiterEx {

    private final int maxRequests;
    private final int timeWindow; // in milliseconds
    private final HashMap<String, PriorityQueue<Long>> userRequestMap;

    public RateLimiterEx(int maxRequests, int timeWindow) {
        this.maxRequests = maxRequests;
        this.timeWindow = timeWindow;
        this.userRequestMap = new HashMap<>();
    }

    public static void main(String[] args) {
        // Initialize with 3 requests per 1000ms (1 second)
        RateLimiterEx rateLimiter = new RateLimiterEx(3, 1000);

        // Simulate user requests
        long now = System.currentTimeMillis();
        System.out.println(rateLimiter.allowRequest("user1", now));       // true
        System.out.println(rateLimiter.allowRequest("user1", now + 100)); // true
        System.out.println(rateLimiter.allowRequest("user1", now + 200)); // true
        System.out.println(rateLimiter.allowRequest("user1", now + 300)); // false
        System.out.println(rateLimiter.allowRequest("user1", now + 1200));// true (timestamp > window)
    }

    public boolean allowRequest(String userId, long timestamp) {
        userRequestMap.putIfAbsent(userId, new PriorityQueue<>());

        PriorityQueue<Long> userQueue = userRequestMap.get(userId);

        // Remove expired timestamps
        while (!userQueue.isEmpty() && timestamp - userQueue.peek() > timeWindow) {
            userQueue.poll();
        }

        if (userQueue.size() < maxRequests) {
            userQueue.add(timestamp);
            return true; // Request allowed
        }

        return false; // Request denied
    }
}
