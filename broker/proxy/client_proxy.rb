require 'json'
require 'socket'

class ClientProxy
  def initialize
    raise 'A child of the ClientProxy class should end in `Client`' unless self.class.name.end_with? 'Client'
    server_name = self.class.name.chomp 'Client'

    @broker_config = JSON.parse IO.read(__dir__ + '/broker.json')
    @endpoint = get_server server_name
  end


  def method_missing(method_sym, *arguments, &block)
    socket = TCPSocket.new @endpoint[:ip], @endpoint[:port]

    server_class = @endpoint[:server_name] + 'Server'
    socket.puts JSON.generate({class: server_class, method: method_sym.to_s, arguments: arguments})
    return_data = socket.gets.chomp
    socket.close

    data = JSON.parse return_data
    raise data['error'] if data.has_key? 'error'

    data['return']
  end


  private
  def get_server(server_name)
    socket = TCPSocket.new @broker_config['ip'], @broker_config['port']

    socket.puts JSON.generate({server_name: server_name})
    response = socket.gets.chomp
    socket.close

    parsed_response = JSON.parse response
    raise parsed_response['error'] if parsed_response.has_key? 'error'

    {server_name: server_name, ip: parsed_response['ip'], port: parsed_response['port']}
  end
end