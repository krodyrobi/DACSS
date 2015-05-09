require_relative 'proxy/client_proxy'

class InfoClient < ClientProxy
  def initialize(server_name)
    super server_name
  end
end

client = InfoClient.new 'Info'
p client.get_temp_city('Los Angeles')
p client.get_road_info(31)
#p client.randomFunc