## LiquidService 

LiquidService is a [Jolie](https://github.com/jolie/jolie) service that exposes the rendering API of the [Liquid template engine](https://shopify.github.io/liquid/) as implemented by the [Liqp](https://github.com/bkiers/Liqp)---plus a few tricks to make it more Jolie-friendly 😉.

## Install 

`jpm add @jolie/liquid`
## Usage example 

```jolie
from @jolie.liquid.main import Liquid
from console import Console

service main(){

  embed Console as Console
  embed Liquid as Liquid

  main {
    template = 
    "{% case language %}"
    + "{% when 'French' %}Salut"
    + "{% when 'Italian' %}Ciao"
    + "{% when 'Spanish' %}Hola"
    + "{% when 'S̄wạs̄dī' %}สวัสดี"
    + "{% when 'Russian' %}привет"
    + "{% else %}Hi"
    + "{% endcase %}"
    + ", {{ name }}"

    data << { 
      name = "Saverio" 
      language = "Italian"
    }
    renderDocument@Liquid({ 
      data << data,
      template = template,
      format = "jolie" 
      })( s )
    println@Console( s )()
  }
}
```
## Jolie Interface

```Jolie
/**
* Loaded templates are useful when sub-templates are necessary
* in the main template (the one used in the renderDocument operation).
* From any template , it is possible to pass data to a loaded template
* with the useTemplate filter, e.g., {{ data | useTemplate: "myTemplate" }}
*/
type LiquidRequest: void {
  .data: string | undefined //< either a json string or a jolie value (set accordingly the `format` node) 
  .format: string( regex( "json|jolie" ) )
  .template: string
}

type LoadRequest: void {
  .template: string
  .name: string
}
```