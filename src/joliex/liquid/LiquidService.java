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
