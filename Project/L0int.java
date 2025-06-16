import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class L0int {

    public static void main(String args[]) {

		InputStream in = null;
		try {
			if (args.length > 0) {
				in = new FileInputStream(args[0]);
			} else {
				in = System.in;
			}
		} catch (FileNotFoundException e) {
			System.err.println("File not found: " + args[0]);
			System.exit(1);
		}


		Parser parser = new Parser(in);
		ASTNode exp;
		
		System.out.println("L0 interpreter PL MEIC 2024/25 (v0.0)\n");

		while (true) {
			try {
				System.out.print("# ");
				exp = parser.Start();
				if (exp==null) System.exit(0);

				exp.typecheck(new Environment<ASTType>());
				IValue v = exp.eval(new Environment<IValue>());
				System.out.println(v.toStr());
			} catch (ParseException e) {
				System.out.println("Syntax Error.");
				parser.ReInit(in);

			} catch (Exception e) {
				e.printStackTrace();
				parser.ReInit(in);
			}
		}
    }
    
}
