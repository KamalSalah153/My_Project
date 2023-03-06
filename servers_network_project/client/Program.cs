using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Net.Sockets;
using System.Net;
namespace client
{
    class Program
    {
        static void Main(string[] args)
        {
            
            IPAddress host = IPAddress.Parse("192.168.1.1");
            //192.168.1.1 refers to the server
            //Put down the IP of the computer next to you.
            //Or 127.0.0.1 if you’ll run both client and server on your machine
            IPEndPoint hostEndpoint = new IPEndPoint(host, 8080);
            //8000 is the port number and must match that of the server.

            Socket clientSock = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);

            clientSock.Connect(hostEndpoint);
            string serverMessage = "";
            string clientMessage = "";
            while (serverMessage.ToLower() != "bye" && clientMessage.ToLower() != "bye")
            {
                //wait for server to send you a message
                byte[] receivedData = new byte[1024];
                int receivedBytesLen = clientSock.Receive(receivedData);
                serverMessage = Encoding.ASCII.GetString(receivedData, 0, receivedBytesLen);
                Console.WriteLine("Server Said: {0}", serverMessage);
                //Send Server a message
                Console.WriteLine("Write your message: ");
                clientMessage = Console.ReadLine();
                byte[] messageByteArray = Encoding.ASCII.GetBytes(clientMessage);
                clientSock.Send(messageByteArray);
                Console.WriteLine("Message has been sent.");
            }
            Console.WriteLine("Conversation Ended");
            clientSock.Shutdown(SocketShutdown.Both);
            clientSock.Close();


        }
    }
}
