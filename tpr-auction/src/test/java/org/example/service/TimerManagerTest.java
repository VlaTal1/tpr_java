package org.example.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TimerManagerTest {

    private TimerManager timerManager;
    private ScheduledExecutorService schedulerMock;
    private ConcurrentHashMap<Long, ScheduledFuture<?>> auctionTimersMock;

    @BeforeEach
    public void setUp() {
        schedulerMock = mock(ScheduledExecutorService.class);
        auctionTimersMock = spy(new ConcurrentHashMap<>());

        timerManager = new TimerManager();
        setField(timerManager, "scheduler", schedulerMock);
        setField(timerManager, "auctionTimers", auctionTimersMock);
    }

    @Test
    public void testScheduleTimer() {
        Long auctionId = 1L;
        Runnable task = () -> System.out.println("Task executed");
        long delay = 1L;
        TimeUnit unit = TimeUnit.SECONDS;

        ScheduledFuture scheduledFutureMock = mock(ScheduledFuture.class);
        when(schedulerMock.schedule(any(Runnable.class), eq(delay), eq(unit))).thenReturn(scheduledFutureMock);

        timerManager.scheduleTimer(auctionId, task, delay, unit);

        verify(schedulerMock).schedule(any(Runnable.class), eq(delay), eq(unit));
        assertEquals(scheduledFutureMock, auctionTimersMock.get(auctionId));
    }

    @Test
    public void testCancelTimer() {
        Long auctionId = 1L;

        ScheduledFuture<?> scheduledFutureMock = mock(ScheduledFuture.class);
        auctionTimersMock.put(auctionId, scheduledFutureMock);

        timerManager.cancelTimer(auctionId);

        verify(scheduledFutureMock).cancel(false);
        assertNull(auctionTimersMock.get(auctionId));
    }

    private void setField(Object targetObject, String fieldName, Object fieldValue) {
        try {
            var field = targetObject.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(targetObject, fieldValue);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
