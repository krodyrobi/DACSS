require_relative 'proxy/client_proxy'

class InfoClient < ClientProxy; end

client = InfoClient.new
p client.get_temp_city('Los Angeles')
p client.get_road_info(31)
#p client.randomFunc