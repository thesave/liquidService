## LiquidService 

LiquidService is a [Jolie](https://github.com/jolie/jolie) service that exposes the rendering API of the Liquid template engine as implemented by the [Liqp](https://github.com/bkiers/Liqp).

## Install and Execution

Download the latest [released archive](https://github.com/thesave/liquidService/releases/) and extract the files in the project, e.g., in folder `liquid`.
To use the library in your Jolie program:

- make sure to include it in your Jolie program with command `include "liquid/include/liquid.iol"` (i.e., include the `liquid.iol` file within the extracted folder
- launch your program with the command `jolie -l "liquid/lib:liquid/lib/*" yourProgram.ol` to let the Jolie interpreter find the Java service wrapping Liqp (liquidService.jar) and its libraries.

## Jolie Interface

```Jolie
type LiquidRequest: void {
  .data: undefined
  .format: string
  .template: string
}

/**
* Loaded templates are useful when sub-templates are necessary
* in the main template (the one used in the renderDocument operation).
* From any template , it is possible to pass data to a loaded template
* with the useTemplate filter, e.g., {{ data | useTemplate: templateName }}
*/
type LoadRequest: void {
  .name: string
  .template: string
}

interface LiquidInterface {
  RequestResponse: renderDocument( LiquidRequest )( string ),
  loadTemplate( LoadRequest )( void )
}
```
