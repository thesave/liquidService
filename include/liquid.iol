type LiquidRequest: void {
  .data: undefined
  .format: string
  .template: string
}

interface LiquidInterface {
  RequestResponse: renderDocument( LiquidRequest )( string )
}

outputPort Liquid {
  Interfaces: LiquidInterface
}

embedded {
  Java: "joliex.liquid.LiquidService" in Liquid
}