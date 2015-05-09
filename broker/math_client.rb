require_relative 'proxy/client_proxy'

class MathClient < ClientProxy
  def initialize(server_name)
    super server_name
  end
end

client = MathClient.new 'Math'
p client.add(1, 2.3)
p client.sqrt(9.1)
#p client.randomFunc