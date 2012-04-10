/*
 * Copyright 2008-2012 Xebia and the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fr.xebia.cocktail;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Javamail based mailer.
 * 
 * @author <a href="mailto:cleclerc@xebia.fr">Cyrille Le Clerc</a>
 */
@Service
public class MailService {

    private Session mailSession;

    protected final Logger auditLogger = LoggerFactory.getLogger("audit");

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected InternetAddress fromAddress;

    @Inject
    public MailService(@Named("smtpProperties") Properties smtpProperties) throws MessagingException {

        if (Strings.isNullOrEmpty(smtpProperties.getProperty("mail.username"))) {
            logger.info("Initialize anonymous mail session");
            mailSession = Session.getInstance(smtpProperties);
        } else {
            final String username = smtpProperties.getProperty("mail.username");
            final String password = smtpProperties.getProperty("mail.password");
            logger.info("Initialize mail session with username='{}', password='xxx'", username);

            mailSession = Session.getInstance(smtpProperties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });
        }

        fromAddress = new InternetAddress(smtpProperties.getProperty("mail.from"));
    }

    public void sendCocktail(Cocktail cocktail, String recipient, String cocktailPageUrl) throws MessagingException {

        Message msg = new MimeMessage(mailSession);

        msg.setFrom(fromAddress);
        msg.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

        msg.setSubject("[Cocktail] " + cocktail.getName());
        String message = cocktail.getName() + "\n" //
                + "--------------------\n" //
                + "\n" //
                + Strings.nullToEmpty(cocktail.getInstructions()) + "\n" //
                + "\n" //
                + cocktailPageUrl;
        msg.setContent(message, "text/plain");

        Transport.send(msg);
        auditLogger.info("Sent to {} cocktail '{}'", recipient, cocktail.getName());

    }

}
