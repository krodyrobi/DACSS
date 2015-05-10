require_relative 'proxy/client_proxy'

class MathClient < ClientProxy; end

client = MathClient.new
p client.add(1, 2.3)
p client.sqrt(9.1)
#p client.randomFunc