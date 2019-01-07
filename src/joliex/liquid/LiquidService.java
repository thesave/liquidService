package joliex.liquid;

import jolie.runtime.AndJarDeps;
import jolie.runtime.FaultException;
import jolie.runtime.JavaService;
import jolie.runtime.Value;
import jolie.runtime.embedding.RequestResponse;
import liqp.Template;

@AndJarDeps( { "liqp.jar", "jsoup.jar", "jackson-databind.jar", 
	"jackson-annotations.jar", "antlr4-runtime.jar", "jackson-core.jar" } )

public class LiquidService extends JavaService
{
	
	@RequestResponse
	public Value renderDocument( Value request ) throws FaultException
	{
		if ( request.getFirstChild( "format" ).isDefined()
			&& request.getFirstChild( "format" ).strValue().toLowerCase().equals( "json" ) ) {
			Template template = Template.parse( request.getFirstChild( "template" ).strValue() );
			return Value.create( template.render( request.getFirstChild( "data" ).strValue() ) );
		} else {
			throw new FaultException( "IOException", "Currently only the JSON data format is supported" );
		}
	}
}