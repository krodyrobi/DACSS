require_relative 'proxy/server_proxy'

class MathServer < ServerProxy
  def self.sqrt(number)
    Math.sqrt(number)
  end


  def self.add(a, b)
    a + b
  end
end

MathServer.new