require_relative 'proxy/client_proxy'

class InfoClient < ClientProxy
  def initialize(server_name)
    super server_name
  end
end

InfoClient.new 'Info'