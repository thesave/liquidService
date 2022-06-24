from liquid.main import Liquid
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
    + "{% when 'Russian' %}привет"
    + "{% when 'Thai' %}สวัสดี"
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
