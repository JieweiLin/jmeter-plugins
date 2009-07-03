package kg.apc.jmeter.dotchart;

import java.util.Arrays;
import java.util.HashMap;
import org.apache.jmeter.samplers.SampleResult;

/**
 *
 * @author apc
 */
public class DotChartModel
     extends HashMap
{
   private int maxThreads;
   private long maxTime;

   public DotChartModel()
   {
      super(0);
   }

   public void addSample(SampleResult res)
   {
      if (res.getGroupThreads()==0)
         return;

      String label = res.getSampleLabel();
      SamplingStatCalculatorColored row;
      if (containsKey(label))
         row = (SamplingStatCalculatorColored) get(label);
      else
      {
         row = new SamplingStatCalculatorColored(label);
         put(label, row);
      }

      row.addSample(res);
      calculateAggregates(res);
   }

   public SamplingStatCalculatorColored get(String key)
   {
      return (SamplingStatCalculatorColored) super.get(key);
   }

   public int getMaxThreads()
   {
      return maxThreads;
   }

   long getMaxTime()
   {
      return maxTime;
   }

   private void calculateAggregates(SampleResult res)
   {
      int threads = res.getAllThreads();
      if (threads > maxThreads)
         maxThreads = threads;
      //avgThreads = (avgThreads * count + threads) / (count + 1);
      long time = res.getTime();
      if (time > maxTime)
         maxTime = time;
      //avgTime = (avgTime * count + time) / (count + 1);
      //count++;
   }

   @Override
   public void clear()
   {
      super.clear();
      maxThreads = 0;
      maxTime = 0;
   }
}
