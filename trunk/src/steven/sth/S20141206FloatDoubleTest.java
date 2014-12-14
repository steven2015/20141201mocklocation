/**
 *
 */
package steven.sth;

/**
 * @author steven.lam.t.f
 *
 */
public class S20141206FloatDoubleTest{
	public static final void main(final String[] args){
		final double a = 22.341768;
		double b = a + 0.000051234125;
		b += 0.000051234125;
		b += 0.000051234125;
		b += 0.000051234125;
		b += 0.000051234125;
		b += 0.000051234125;
		final float d = (float)b;
		final double c = d;
		System.out.println(d);
		System.out.println(c);
		System.out.println((float)(c));
		final float f = (float)22.342075;
		final double e = Double.parseDouble(String.valueOf(f));
		System.out.println(e);
		System.out.println("        \n");
	}
}
