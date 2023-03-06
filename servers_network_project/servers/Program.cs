using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Net.Sockets;
using System.Net;
namespace servers
{
    class Program
    {
        static void Main(string[] args)
        {
            IPEndPoint ipEnd = new IPEndPoint(IPAddress.Any, 8080);
            //8000 is any unused port
            //127.0.0.1 refers to the local computer (localhost)

            Socket sock = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);

            sock.Bind(ipEnd);

            sock.Listen(5);
            Socket clientSock = sock.Accept();
            string serverMessage = "";
            string clientMessage = "";
            while (serverMessage.ToLower() != "bye" && clientMessage.ToLower() != "bye")
            {
                //Send Client a message
                Console.WriteLine("Write your message: ");
                serverMessage = Console.ReadLine();
                byte[] messageByteArray = Encoding.ASCII.GetBytes(serverMessage);
                clientSock.Send(messageByteArray);
                Console.WriteLine("Message has been sent.");
                //Wait for client to send you a message.
                messageByteArray = new byte[1024];
                int receivedBytesLen = clientSock.Receive(messageByteArray);
                clientMessage = Encoding.ASCII.GetString(messageByteArray, 0, receivedBytesLen);
                Console.WriteLine("Client said: {0}", clientMessage);
            }
            Console.WriteLine("Conversation Ended");
            clientSock.Shutdown(SocketShutdown.Both);
            clientSock.Close();



        }
    }
}
