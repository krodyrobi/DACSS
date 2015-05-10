require 'json'
require 'socket'


class Broker
  def initialize(config)
    @server_hash = {}
    @server = TCPServer.new config[:ip], 0

    config[:port] = @server.addr[1]
    @broker_config = config
    dump_config

    run
  end


  private

  def dump_config(path = __dir__ + '/proxy/broker.json')
    IO.write path, JSON.generate(@broker_config)
  end


  def run
    loop do
      client = @server.accept
      client.puts process( client.gets.chomp )
      client.close
    end
  end


  def process(input)
    begin
      input = JSON.parse input
    rescue
      return message_invalid  #Parse error could kill the broker for everybody :D
    end

    size = input.length
    server_default = @broker_config[:server]

    if size == 0 or size > 3 or not input.has_key? 'server_name'
      message = message_invalid
    else
      server_name = input['server_name'].to_sym
      server_registered = @server_hash.has_key? server_name

      if size == 1
        message = server_registered ? message_found( server_name ) : message_not_found( server_name )
      else
        port = input.fetch 'port', server_default[:port]
        ip = input.fetch 'ip', server_default[:ip]

        config = {ip: ip, port: port}
        @server_hash[ server_name ] = config

        message = message_stored server_name, config
      end
    end

    message
  end


  def message_invalid
    JSON.generate({ error: 'Invalid request' })
  end


  def message_not_found( server_name )
    JSON.generate({error: "#{server_name.to_s} not found"})
  end


  def message_found( server_name )
    config = @server_hash[ server_name ]

    JSON.generate({
      server_name: server_name.to_s,
      ip:   config[:ip],
      port: config[:port]
    })
  end


  def message_stored( server_name, config )
    JSON.generate({success: "Registered #{server_name.to_s} at #{config[:ip]}:#{config[:port]}"})
  end
end

Broker.new({ip: 'localhost', server: { ip: 'localhost', port: 1111}})
