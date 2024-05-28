package org.example.service;

import org.springframework.stereotype.Component;

import java.util.concurrent.*;

@Component
public class TimerManager {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(10);

    private final ConcurrentHashMap<Long, ScheduledFuture<?>> auctionTimers = new ConcurrentHashMap<>();

    public void scheduleTimer(Long auctionId, Runnable task, long delay, TimeUnit unit) {
        ScheduledFuture<?> auctionTimer = scheduler.schedule(task, delay, unit);
        auctionTimers.put(auctionId, auctionTimer);
    }

    public void cancelTimer(Long auctionId) {
        ScheduledFuture<?> existingTimer = auctionTimers.get(auctionId);
        if (existingTimer != null) {
            existingTimer.cancel(false);
        }
        auctionTimers.remove(auctionId);
    }
}

