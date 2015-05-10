require 'json'
require 'socket'

class ServerProxy
  def initialize(ip = 'localhost')
    @broker_config = JSON.parse IO.read(__dir__ + '/broker.json')
    @server_socket = TCPServer.new 0

    raise 'A child of the ServerProxy class should end in `Server`' unless self.class.name.end_with? 'Server'
    server_name = self.class.name.chomp 'Server'
    @server = register_server server_name, ip, @server_socket.addr[1]

    run
  end


  private
  def register_server(server_name, ip, port)
    data = {server_name: server_name, ip: ip, port: port}

    socket = TCPSocket.new @broker_config['ip'], @broker_config['port']
    socket.puts JSON.generate data
    response = socket.gets.chomp
    socket.close

    parsed_response = JSON.parse(response)
    raise parsed_response['error'] if parsed_response.has_key? 'error'

    data
  end


  def run
    loop do
      client = @server_socket.accept
      client.puts process( client.gets.chomp )
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