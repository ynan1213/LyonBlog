package com.ynan._05.atomic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceMain
{
    private static AtomicReference<BankCard> bankCardRef = new AtomicReference<>(new BankCard("cxuan", 100));

    public static void main(String[] args)
    {
        for (int i = 0; i < 10; i++)
        {
            new Thread(() -> {
                while (true)
                {
                    final BankCard card = bankCardRef.get();
                    BankCard newCard = new BankCard(card.getAccountName(), card.getMoney() + 100);
                    if (bankCardRef.compareAndSet(card, newCard))
                    {
                        System.out.println(newCard);
                        break;
                    }

                    try
                    {
                        TimeUnit.MICROSECONDS.sleep(1000);
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}


class BankCard
{
    private final String accountName;
    private final int money;

    public BankCard(String accountName, int money)
    {
        this.accountName = accountName;
        this.money = money;
    }

    public String getAccountName()
    {
        return accountName;
    }

    public int getMoney()
    {
        return money;
    }

    @Override
    public String toString()
    {
        return "BankCard{" +
                "accountName='" + accountName + '\'' +
                ", money='" + money + '\'' +
                '}';
    }
}