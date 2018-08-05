package br.com.felipe.exceptions;

public class TratadorDeExcecao implements Thread.UncaughtExceptionHandler {

   @Override
   public void uncaughtException(Thread t, Throwable e) {
       System.out.println("Deu exceção na thread " + t.getName() + ", "
               + e.getMessage());
       }
   }


