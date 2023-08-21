package FizzBuzz;

import java.util.concurrent.Semaphore;

class FizzBuzz {
    private final int n;
    private final Semaphore semFizz;
    private final Semaphore semBuzz;
    private final Semaphore semFizzBuzz;
    private final Semaphore semNumber;

    public FizzBuzz(int n) {
        this.n = n;
        this.semFizz = new Semaphore(0);
        this.semBuzz = new Semaphore(0);
        this.semFizzBuzz = new Semaphore(0);
        this.semNumber = new Semaphore(1);
    }

    public void fizz() throws InterruptedException {
        for (int i = 3; i <= n; i += 3) {
            if (i % 5 != 0) {
                semFizz.acquire();
                System.out.println("fizz");
                semNumber.release();
            }
        }
    }

    public void buzz() throws InterruptedException {
        for (int i = 5; i <= n; i += 5) {
            if (i % 3 != 0) {
                semBuzz.acquire();
                System.out.println("buzz");
                semNumber.release();
            }
        }
    }

    public void fizzbuzz() throws InterruptedException {
        for (int i = 15; i <= n; i += 15) {
            semFizzBuzz.acquire();
            System.out.println("fizzbuzz");
            semNumber.release();
        }
    }

    public void number() throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            semNumber.acquire();
            if (i % 3 == 0 && i % 5 == 0) {
                semFizzBuzz.release();
            } else if (i % 3 == 0) {
                semFizz.release();
            } else if (i % 5 == 0) {
                semBuzz.release();
            } else {
                System.out.println(i);
                semNumber.release();
            }
        }
    }

    public static void main(String[] args) {
        int n = 15;
        FizzBuzz fizzBuzz = new FizzBuzz(n);

        Thread threadA = new Thread(() -> {
            try {
                fizzBuzz.fizz();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread threadB = new Thread(() -> {
            try {
                fizzBuzz.buzz();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread threadC = new Thread(() -> {
            try {
                fizzBuzz.fizzbuzz();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread threadD = new Thread(() -> {
            try {
                fizzBuzz.number();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        threadA.start();
        threadB.start();
        threadC.start();
        threadD.start();
    }
}