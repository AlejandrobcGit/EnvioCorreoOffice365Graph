package com.example;

import java.util.List;

import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.microsoft.graph.models.BodyType;
import com.microsoft.graph.models.EmailAddress;
import com.microsoft.graph.models.ItemBody;
import com.microsoft.graph.models.Message;
import com.microsoft.graph.models.Recipient;
import com.microsoft.graph.serviceclient.GraphServiceClient;
import com.microsoft.graph.users.item.sendmail.SendMailPostRequestBody;

import io.github.cdimascio.dotenv.Dotenv;

public class SendMail {

    public static void main(String[] args) {
        // === CONFIG ===
        // carga variables desde un archivo .env 
        Dotenv dotenv = Dotenv.load();


        // Requeridas:
        String tenantId     = dotenv.get("tenantId");
        String clientId     = dotenv.get("clientId");dotenv.get("clientId");
        String clientSecret = dotenv.get("clientSecret");

        // El buzón desde el que vas a enviar (UPN o id). En app-only debes usar /users/{id|upn}/sendMail. [1](https://learn.microsoft.com/en-us/graph/api/user-sendmail?view=graph-rest-1.0)
        String senderUpn    = dotenv.get("senderUpn");

        // Correo destino
        String to           = dotenv.get("to");

        // Contenido
        String subject      = "Prueba Microsoft Graph (App-Only)";
        String bodyText     = "Hola! Este correo fue enviado con Microsoft Graph desde Java.";

        // Scope para client credentials: resource + /.default [4](https://learn.microsoft.com/en-us/entra/identity-platform/scenario-daemon-acquire-token)
        String[] scopes = new String[] { "https://graph.microsoft.com/.default" };

        // === AUTH (Client Credentials) ===
        ClientSecretCredential credential = new ClientSecretCredentialBuilder()
                .tenantId(tenantId)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .build();

        // Graph client (SDK Java) [6](https://github.com/microsoftgraph/msgraph-sdk-java)
        GraphServiceClient graphClient = new GraphServiceClient(credential, scopes);

        // === BUILD MESSAGE ===
        Message message = new Message();
        message.setSubject(subject);

        ItemBody itemBody = new ItemBody();
        itemBody.setContentType(BodyType.Text);
        itemBody.setContent(bodyText);
        message.setBody(itemBody);

        EmailAddress emailAddress = new EmailAddress();
        emailAddress.setAddress(to);

        Recipient recipient = new Recipient();
        recipient.setEmailAddress(emailAddress);

        message.setToRecipients(List.of(recipient));

        // Request body del sendMail (modelo del SDK) [7](https://learn.microsoft.com/en-us/graph/tutorials/java-email)
        SendMailPostRequestBody postBody = new SendMailPostRequestBody();
        postBody.setMessage(message);
        postBody.setSaveToSentItems(true);

        // === SEND ===
        // Endpoint equivalente: POST /users/{id|userPrincipalName}/sendMail [1](https://learn.microsoft.com/en-us/graph/api/user-sendmail?view=graph-rest-1.0)
        graphClient.users().byUserId(senderUpn)
                .sendMail()
                .post(postBody);

        System.out.println("Correo enviado con éxito (solicitud aceptada).");
        System.out.println("   Remitente: " + senderUpn);
        System.out.println("   Destino:   " + to);
        System.out.println("   Asunto:    " + subject);
    }
}