require_relative 'proxy/server_proxy'

class InfoServer < ServerProxy
  def initialize(server_name, ip='localhost')
    start_server server_name, ip
  end


  def self.get_road_info(id)
    states = ['under construction', 'finished', 'closed']
    "Road #{id} is #{states.sample}"
  end


  def self.get_temp_city(city)
    "The temperature in city #{city} is #{rand(-40..40)}"
  end
end

InfoServer.new 'Info'