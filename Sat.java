package assignments;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

public class Sat {

  /**
   * @param args
   * @throws Exception
   */

  public static boolean[] x;
  public static int[][] expr;
  public static boolean[] e;
  public static int n = 100000;
  public static ArrayList<Integer> falses;
  public static ArrayList<Integer> trues;

  @SuppressWarnings("unchecked")
  public static void main(String[] args) throws Exception {

    falses = new ArrayList<Integer>();
    trues = new ArrayList<Integer>();
    x = new boolean[n];
    expr = new int[2][n];

    ArrayList<Integer> arlist = null;

    initX(x);
    initExpression("e:\\home\\2sat1.txt");

    Object[] arr = new Object[n];
    for (int i = 0; i < n; i++) {
      arr[i] = new ArrayList<Integer>();
    }

    for (int i = 0; i < n; i++) {
      arlist = (ArrayList<Integer>) arr[Math.abs(expr[0][i])];
      arlist.add(i);
      arlist = (ArrayList<Integer>) arr[Math.abs(expr[1][i])];
      arlist.add(i);

    }
    System.out.println(falses.size());
    System.out.println(trues.size());
    for (int k = 0; k < 150000; k++) {

      if (k % 10000 == 0) {
        System.out.println(k);
        System.out.println(falses.size());
      }

      Random random = new Random();

      if (falses.size() == 0) {
        System.out.println(falses.size());
        System.out.println(trues.size());

        for (int h = 0; h < n; h++) {
          int[] b = new int[2];
          b[0] = expr[0][h];
          b[1] = expr[1][h];
          if (!eval(b)) {
            System.out.println("not satisfied");
          }
        }

        return;
      }
      int r = random.nextInt(falses.size());
      int f = falses.get(r);
      if (random.nextBoolean()) {
        x[Math.abs(expr[0][f])] = !x[Math.abs(expr[0][f])];
        ArrayList<Integer> l = (ArrayList<Integer>) arr[Math.abs(expr[0][f])];
        for (int p : l) {
          int v[] = new int[2];
          v[0] = expr[0][p];
          v[1] = expr[1][p];
          if (eval(v)) {
            falses.remove(new Integer(p));
            if (!trues.contains(new Integer(p))) {
              trues.add(new Integer(p));
            }

          } else {
            if (!falses.contains(new Integer(p))) {
              falses.add(new Integer(p));
            }

            trues.remove(new Integer(p));
          }
        }

      } else {
        x[Math.abs(expr[1][f])] = !x[Math.abs(expr[1][f])];
        ArrayList<Integer> l = (ArrayList<Integer>) arr[Math.abs(expr[1][f])];
        for (int p : l) {
          int v[] = new int[2];
          v[0] = expr[0][p];
          v[1] = expr[1][p];
          if (eval(v)) {
            falses.remove(new Integer(p));

            if (!trues.contains(new Integer(p))) {
              trues.add(new Integer(p));
            }
          } else {
            if (!falses.contains(new Integer(p))) {
              falses.add(new Integer(p));
            }
            trues.remove(new Integer(p));
          }
        }
      }
    }

    System.out.println(falses.size());

  }

  public static void initX(boolean[] x) {
    Random random = new Random();
    for (int i = 0; i < x.length; i++) {
      x[i] = random.nextBoolean();
    }
  }

  public static boolean eval(int[] expr) {
    boolean a, b;
    if (expr[0] < 0) {
      a = !x[-expr[0]];
    } else {
      a = x[expr[0]];
    }
    if (expr[1] < 0) {
      b = !x[-expr[1]];
    } else {
      b = x[expr[1]];
    }

    return a | b;

  }

  public static int initExpression(String filename) throws Exception {

    int i = 0;
    BufferedReader br = null;
    try {
      String line;
      br = new BufferedReader(new FileReader(filename));

      while ((line = br.readLine()) != null) {
        String[] s = line.split(" ");
        if (s.length > 1) {
          int n1 = Integer.parseInt(s[0]);
          int n2 = Integer.parseInt(s[1]);

          if (n1 > 0) {
            expr[0][i] = n1 - 1;
          } else {
            expr[0][i] = n1 + 1;
          }

          if (n2 > 0) {
            expr[1][i] = n2 - 1;
          } else {
            expr[1][i] = n2 + 1;
          }

          int v[] = new int[2];
          v[0] = expr[0][i];
          v[1] = expr[1][i];

          if (eval(v)) {
            trues.add(i);
          } else {
            falses.add(i);
          }

          i++;
        }
      }
    } finally {
      br.close();
    }
    return i;
  }
}