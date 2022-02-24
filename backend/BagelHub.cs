using System.Threading;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.SignalR;
using Microsoft.Azure.WebJobs;
using Microsoft.Azure.WebJobs.Extensions.Http;
using Microsoft.Azure.WebJobs.Extensions.SignalRService;

namespace Bagels;

internal class BagelCount
{
  private int _bagelCount = 0;

  public int Increment() => ThreadSafeChange(1);

  public int Decrement() => ThreadSafeChange(-1);

  public int Get() => _bagelCount;

  private int ThreadSafeChange(int appendValue)
  {
    int initialValue, newValue;
    do
    {
      initialValue = _bagelCount;
      newValue = initialValue + appendValue;
    } while (
      newValue is >= 0 and <= 8
      && initialValue != Interlocked.CompareExchange(ref _bagelCount, newValue, initialValue));

    return _bagelCount;
  }
}

public class BagelHub : ServerlessHub
{
  private static readonly BagelCount BagelCount = new BagelCount();

  [FunctionName("negotiate")]
  public SignalRConnectionInfo Negotiate([HttpTrigger(AuthorizationLevel.Anonymous)] HttpRequest req)
  {
    return Negotiate();
  }

  [FunctionName(nameof(OnConnected))]
  public async Task OnConnected(
    [SignalRTrigger] InvocationContext invocationContext)
  {
    await Clients
      .Client(invocationContext.ConnectionId)
      .SendAsync("UpdatedBagelCount", BagelCount.Get());
  }

  [FunctionName(nameof(OnDisconnected))]
  public async Task OnDisconnected([SignalRTrigger] InvocationContext invocationContext)
  {
    await Task.CompletedTask;
  }

  [FunctionName(nameof(DecrementBagelCount))]
  public async Task DecrementBagelCount(
    [SignalRTrigger] InvocationContext invocationContext)
  {
    var count = BagelCount.Decrement();
    await Clients
      .All
      .SendAsync("UpdatedBagelCount", count);
  }

  [FunctionName(nameof(IncrementBagelCount))]
  public async Task IncrementBagelCount(
    [SignalRTrigger] InvocationContext invocationContext)
  {
    var count = BagelCount.Increment();
    await Clients
      .All
      .SendAsync("UpdatedBagelCount", count);
  }
}


