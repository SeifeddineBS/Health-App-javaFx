/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

/**
 *
 * @author SeifBS
 */
public interface ISendMail {

    public void envoyerMail(String email, String Subject, String Object);

    public void send_mail_nour(String from, String pass, String to, String object, String subject);

}
