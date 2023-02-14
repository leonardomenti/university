import java.lang.reflect.*;

public class InvokeUnknownMethod{
	public static void main(String[] args) throws Throwable{
		if (args.length > 2){
			
			Class cls = Class.forName(args[0]);
			String mthName = args[1];

			if (args.length == 3){
				double x = Double.parseDouble(args[2]);
				Method mth = cls.getDeclaredMethod(mthName, new Class[]{double.class});
				double ret = (double) mth.invoke(new Calculator(), new Object[]{x});
				System.out.println(ret);
			}
			else{
				try{
					int a_int = Integer.parseInt(args[2]);
					int b_int = Integer.parseInt(args[3]);
					Method mth = cls.getDeclaredMethod(mthName, new Class[]{int.class, int.class});
					int ret = (int) mth.invoke(new Calculator(), new Object[]{a_int, b_int});
					System.out.println(ret);
				} catch (NumberFormatException nfe){
					double a_double = Double.parseDouble(args[2]);
					double b_double = Double.parseDouble(args[3]);
					Method mth = cls.getDeclaredMethod(mthName, new Class[]{double.class, double.class});
					double ret = (double) mth.invoke(new Calculator(), new Object[]{a_double, b_double});
					System.out.println(ret);
				}
			}
					
		}
	}
}