require 'json'
require 'socket'


class Broker
  # config has to be a hash with {hostname: "localhost", port: 2000, default_server_ip: "localhost", default_server_port: 1111}
  def initialize(config)
    @server_hash = {}
    @config = config
    @server = TCPServer.new @config[:hostname], @config[:port]

    run
  end


  private

  def run
    loop do
      client = @server.accept
      client.puts( process( client.gets.chomp ) )
      client.close
    end
  end


  def process(input)
    input = JSON.parse input
    size = input.length

    if size == 0 or size > 3 or not input.has_key? 'server_name'
      message = message_invalid
    else
      server_name = input['server_name'].to_sym
      server_registered = @server_hash.has_key? server_name

      if size == 1
        message = server_registered ? message_found( server_name ) : message_not_found( server_name )
      else
        port = ( input.has_key? 'port' ) ? input['port'].to_i : @config[:default_server_port]
        ip = ( input.has_key? 'ip' ) ? input['ip'].to_s : @config[:default_server_ip]

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

Broker.new({hostname: 'localhost', port: 2000, default_server_ip: 'localhost', default_server_port: 1111})
