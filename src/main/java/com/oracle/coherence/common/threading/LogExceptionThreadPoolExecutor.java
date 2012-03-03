/*
 * File: LogExceptionThreadPoolExecutor.java
 * 
 * Copyright (c) 2010. All Rights Reserved. Oracle Corporation.
 * 
 * Oracle is a registered trademark of Oracle Corporation and/or its affiliates.
 * 
 * This software is the confidential and proprietary information of Oracle
 * Corporation. You shall not disclose such confidential and proprietary
 * information and shall use it only in accordance with the terms of the license
 * agreement you entered into with Oracle Corporation.
 * 
 * Oracle Corporation makes no representations or warranties about the
 * suitability of the software, either express or implied, including but not
 * limited to the implied warranties of merchantability, fitness for a
 * particular purpose, or non-infringement. Oracle Corporation shall not be
 * liable for any damages suffered by licensee as a result of using, modifying
 * or distributing this software or its derivatives.
 * 
 * This notice may not be removed or altered.
 */

package com.oracle.coherence.common.threading;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>A {@link LogExceptionThreadPoolExecutor} is a ThreadPoolExecutor that logs the potential
 * exceptions being thrown during the execution of a Runnable. </p>
 *
 * @author Christer Fahlgren
 */
public class LogExceptionThreadPoolExecutor extends ThreadPoolExecutor implements ObservableExecutor
{

    /**
     * The {@link Logger} to use.
     */
    private static Logger logger = Logger.getLogger(LogExceptionThreadPoolExecutor.class.getName());

    /**
     * The callback method to call before starting and after completion of a Runnable. 
     */
    private ExecutorListener callback;


    /**
     * Standard constructor.
     * 
     * @param corePoolSize      the default number of threads 
     * @param maximumPoolSize   the maximum number of threads
     * @param keepAliveTime     how long to keep threads alive
     * @param unit              TimeUnit for the keepAliveTime
     * @param workQueue         the queue to keep work in 
     */
    public LogExceptionThreadPoolExecutor(int corePoolSize,
                                          int maximumPoolSize,
                                          long keepAliveTime,
                                          TimeUnit unit,
                                          BlockingQueue<Runnable> workQueue)
    {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }


    /**
     * Standard constructor.
     * 
     * @param corePoolSize      the default number of threads 
     * @param maximumPoolSize   the maximum number of threads
     * @param keepAliveTime     how long to keep threads alive
     * @param unit              TimeUnit for the keepAliveTime
     * @param workQueue         the queue to keep work in 
     * @param threadFactory     the ThreadFactory to use
     */
    public LogExceptionThreadPoolExecutor(int corePoolSize,
                                          int maximumPoolSize,
                                          long keepAliveTime,
                                          TimeUnit unit,
                                          BlockingQueue<Runnable> workQueue,
                                          ThreadFactory threadFactory)
    {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }


    /**
     * Called after the execution of a Runnable. Checks if an exception was thrown and logs it in that case.
     * @param r   the Runnable
     * @param t   the Throwable 
     */
    protected void afterExecute(Runnable r,
                                Throwable t)
    {
        super.afterExecute(r, t);
        if (t == null && r instanceof Future<?>)
        {
            try
            {
                if (((Future<?>) r).isDone())
                {
                    ((Future<?>) r).get();
                }
            }
            catch (CancellationException ce)
            {
                t = ce;
            }
            catch (ExecutionException ee)
            {
                t = ee.getCause();
            }
            catch (InterruptedException ie)
            {
                Thread.currentThread().interrupt(); // ignore/reset
            }
            catch (Throwable th)
            {
                t = th;
            }
        }

        if (callback != null)
        {
            callback.afterExecute(r, t);
        }
        if (t != null)
        {
            logger.log(Level.SEVERE, "Exception {0} thrown during execution of {1}.", new Object[] { t, r });
            StringWriter resultStringWriter = new StringWriter();
            PrintWriter pw = new PrintWriter(resultStringWriter);
            t.printStackTrace(pw);
            logger.log(Level.SEVERE, "{0}", resultStringWriter.toString());
        }
    }


    /**
     * Called before the execution of a Runnable. 
     * 
     * @param t   the {@link Thread} 
     * @param r   the {@link Runnable}
     */
    @Override
    protected void beforeExecute(Thread t,
                                 Runnable r)
    {
        super.beforeExecute(t, r);
        if (callback != null)
        {
            callback.beforeExecute(r);
        }
    }


    /**
     * {@inheritDoc} 
     */
    public void setCallback(ExecutorListener callback)
    {
        this.callback = callback;
    }
}
