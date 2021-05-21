/************************************************************************************
 *   Copyright (C) 2019-2021 by Saverio Giallorenzo <saverio.giallorenzo@gmail.com> *
 *                                                                                  *
 *   This program is free software; you can redistribute it and/or modify           *
 *   it under the terms of the GNU Library General Public License as                *
 *   published by the Free Software Foundation; either version 2 of the             *
 *   License, or (at your option) any later version.                                *
 *                                                                                  *
 *   This program is distributed in the hope that it will be useful,                *
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of                 *
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                  *
 *   GNU General Public License for more details.                                   *
 *                                                                                  *
 *   You should have received a copy of the GNU Library General Public              *
 *   License along with this program; if not, write to the                          *
 *   Free Software Foundation, Inc.,                                                *
 *   59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.                      *
 *                                                                                  *
 *   For details about the authors of this software, see the AUTHORS file.          *
 ************************************************************************************/

type LiquidRequest: void {
  .data: string | undefined //< either a json string or a jolie value (set accordingly the `format` node) 
  .format: string( regex( "json|jolie" ) )
  .template: string
}

type LoadRequest: void {
  .template: string
  .name: string
}

interface LiquidInterface {
  RequestResponse: 
    renderDocument( LiquidRequest )( string ),
    loadTemplate( LoadRequest )( void )
}

service Liquid() {
  inputPort IP {
    location: "local"
    interfaces: LiquidInterface
  }  

  foreign java {
    class: "joliex.liquid.LiquidService"
  }
}