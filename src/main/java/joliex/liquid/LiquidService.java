/*******************************************************************************
 *   Copyright (C) 2019 by Saverio Giallorenzo <saverio.giallorenzo@gmail.com> *
 *                                                                             *
 *   This program is free software; you can redistribute it and/or modify      *
 *   it under the terms of the GNU Library General Public License as           *
 *   published by the Free Software Foundation; either version 2 of the        *
 *   License, or (at your option) any later version.                           *
 *                                                                             *
 *   This program is distributed in the hope that it will be useful,           *
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of            *
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the             *
 *   GNU General Public License for more details.                              *
 *                                                                             *
 *   You should have received a copy of the GNU Library General Public         *
 *   License along with this program; if not, write to the                     *
 *   Free Software Foundation, Inc.,                                           *
 *   59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.                 *
 *                                                                             *
 *   For details about the authors of this software, see the AUTHORS file.     *
 *******************************************************************************/

package joliex.liquid;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import jolie.runtime.AndJarDeps;
import jolie.runtime.FaultException;
import jolie.runtime.JavaService;
import jolie.runtime.Value;
import jolie.runtime.embedding.RequestResponse;
import jolie.js.JsUtils;
import liqp.Template;
import liqp.filters.Filter;

@AndJarDeps({
				"antlr4-runtime-4.7.2.jar",
				"jackson-annotations-2.10.5.jar",
				"jackson-core-2.10.5.jar",
				"jackson-databind-2.10.5.jar",
				"jsoup-1.12.2.jar",
				"liqp-0.7.9.jar" }
)
public class LiquidService extends JavaService {
	static final HashMap< String, Template > templatesMap = new HashMap<>();

	static {
		Filter.registerFilter( new Filter( "useTemplate" ) {
			@Override
			public Object apply( Object value, Object... params ) {
				ObjectMapper objectMapper = new ObjectMapper();
				String input = "";
				try {
					input = objectMapper.writeValueAsString( value );
				} catch ( JsonProcessingException ex ) {
					Logger.getLogger( LiquidService.class.getName() ).log( Level.SEVERE, null, ex );
				}
				String templateName = super.asString( params[ 0 ] );
				if ( templatesMap.containsKey( templateName ) ) {
					return templatesMap.get( templateName ).render( input );
				} else {
					return "";
				}
			}
		} );
	}

	@RequestResponse
	public void loadTemplate( Value request ) {
		templatesMap.put(
						request.getFirstChild( "name" ).strValue(),
						Template.parse( request.getFirstChild( "template" ).strValue() )
		);
	}

	@RequestResponse
	public Value renderDocument( Value request ) throws FaultException {
		String format = request.getFirstChild( "format" ).strValue();
		String data = null;
		if ( format.equalsIgnoreCase( "jolie" ) ) {
			StringBuilder sb = new StringBuilder();
			try {
				JsUtils.valueToJsonString( request.getFirstChild( "data" ), false, null, sb );
				data = sb.toString();
			} catch ( IOException e ) {
				throw new FaultException( "IOException", e.getMessage() );
			}
		}
		if ( format.equalsIgnoreCase( "json" ) ) {
			data = request.getFirstChild( "data" ).strValue();
		}
		if ( data != null ) {
			Template template = Template.parse( request.getFirstChild( "template" ).strValue() );
			String rendering = template.render( data );
			return Value.create( rendering );
		} else {
			throw new FaultException( "IOException", "Currently only the Jolie or JSON data is supported, provided: " + format );
		}
	}

}
