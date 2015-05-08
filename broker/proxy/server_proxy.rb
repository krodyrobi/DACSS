require 'json'
require 'socket'

class ServerProxy
  def start_server(server_name, ip='localhost', port)
    @config = JSON.parse(IO.read(__dir__ + '/broker.json'))
    @server = register_server server_name, ip, port
    run
  end


  private
  def register_server(server_name, ip, port)
    socket = TCPSocket.new @config['ip'], @config['port']

    p server_name, ip, port
    p @config['ip'], @config['port']
    socket.puts JSON.generate({server_name: server_name, ip: ip, port: port})
    response = socket.gets.chomp
    socket.close

    parsed_response = JSON.parse(response)
    raise parsed_response['error'] if parsed_response.has_key? 'error'

    parsed_response
  end


  def run
    server = TCPServer.new @server['port']

    loop do
      client = server.accept
      client.puts( process( client.gets.chomp ) )
      client.close
    end
  end


  def process(input)
    input = JSON.parse input

    begin
      klass = Object.const_get input['class']
      raise "#{input['class']} not defined as a server" unless klass < ServerProxy
      raise "Invalid method call #{input['method']}" unless klass.public_methods(false).include? input['method'].to_sym

      data = klass.public_send input['method'].to_sym, *input['arguments']
      message = message_return data
    rescue NameError, RuntimeError => e
      message = message_error e.message
    end

    message
  end


  def message_error(string)
    JSON.generate({error: string})
  end


  def message_return(data)
    JSON.generate({return: data})
  end
end