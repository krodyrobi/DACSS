require_relative 'proxy/server_proxy'

class MathServer < ServerProxy
  def initialize(server_name, ip='localhost')
    start_server server_name, ip
  end


  def self.sqrt(number)
    Math.sqrt(number)
  end


  def self.add(a, b)
    a + b
  end
end

MathServer.new 'Math'