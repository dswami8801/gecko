## Welcome to Gecko!
![enter image description here](https://i.etsystatic.com/9923148/r/il/b94aac/786624369/il_794xN.786624369_s718.jpg)
### What Is It?
#### A web Server
Geko is an implementation of servlet like API Specification where we can Implement @Resource Types , Annotate them with endpoint URL's , bundle them in jar and put under the servers working directory.
Geko will scan the jars and prepare a mapping of endpoint of Resources , @Resources are the place where we put the logic to execute by the gecko server. 

We can annotate the class with @Endpoint to map it with a URL like 

    @Endpoint(value = "/greet", method = RequestMethod.GET, produces = "text/html")
    public class GreetingResource implements Resource{
Then @override the doServe method from Resource

    @Override
	public void doServe(HttpRequest httpRequest, HttpResponse httpResponse) {
		LOGGER.info("Invoking doServe");
		httpResponse.setStatusCode(200);
		httpResponse.setStatus("OK");
		httpResponse.addHeader("Content-Type", "text/html");
		httpResponse.setResponse("Hi " + httpRequest.getParam("name"));
		LOGGER.info("responding with headers " + httpResponse);
		httpResponse.commit();
	}
This is a sample Greeting  Resource where we can hit the url from browser by passing the parameter and It returns back with the Greet message.

    http://localhost:9090/jobs/greet?name=john_doe
    Returns : Hi john_doe

 
 

   

  
