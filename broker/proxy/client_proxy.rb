require 'json'
require 'socket'

class ClientProxy
  def initialize(server_name)
    @config = JSON.parse( IO.read(__dir__ + '/broker.json') )
    @endpoint = get_server(server_name)
  end


  def self.method_missing(method_sym, *arguments, &block)
    socket = TCPSocket.new @endpoint['ip'], @endpoint['port']

    socket.puts( JSON.generate({class: self.class.name, method: method_sym.to_s, arguments: arguments}) )
    return_data = socket.gets.chomp
    socket.close

    data = JSON.parse return_data
    raise data['error'] if data.has_key? 'error'

    data['return']
  end


  private
  def get_server(server_name)
    socket = TCPSocket.new @config['ip'], @config['port']

    socket.puts({server_name: server_name})
    response = socket.gets.chomp
    socket.close

    parsed_response = JSON.parse(response)
    raise parsed_response['error'] if parsed_response.has_key? 'error'

    {server_name: server_name, ip: parsed_response['ip'], port: parsed_response['port']}
  end
end