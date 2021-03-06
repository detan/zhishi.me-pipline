package me.zhishi.tools.file;

import me.zhishi.tools.TextTools;
import me.zhishi.tools.URICenter;

public class TripleReader
{
	private String triple;
	
	private int SE;
	private int PE;
	private int OE;
	
	public TripleReader( String triple )
	{
		this.triple = triple;
		SE = triple.indexOf( " <" );
		PE = triple.indexOf( "> ", SE + 1 ) + 1;
		OE = triple.lastIndexOf( "." ) -1;
	}
	
	public String getTriple()
	{
		return triple;
	}
	
	public String getSubject()
	{
		return triple.substring( 0, SE );
	}
	
	public String getBareSubject()
	{
		return triple.substring( 1, SE - 1 );
	}
	
	public String getSubjectContent()
	{
		String str = getSubject();
		return URICenter.zhishiDecode( str.substring( str.lastIndexOf( "/" )+1, str.indexOf( ">" ) ) );
	}
	
	public String getIRISubjectContent()
	{
		String str = getSubject();
		return TextTools.UnicodeToString( str.substring( str.lastIndexOf( "/" )+1, str.indexOf( ">" ) ) );
	}
	
	public String getPredicate()
	{
		return triple.substring( SE + 1, PE );
	}
	
	public String getBarePredicate()
	{
		return triple.substring( SE + 2, PE - 1 );
	}
	
	public String getPredicateContent()
	{
		String str = getPredicate();
		return URICenter.zhishiDecode( str.substring( str.lastIndexOf( "/" )+1, str.indexOf( ">" ) ) );
	}
	
	public String getIRIPredicateContent()
	{
		String str = getPredicate();
		return TextTools.UnicodeToString( str.substring( str.lastIndexOf( "/" )+1, str.indexOf( ">" ) ) );
	}
	
	public String getObject()
	{
		return triple.substring( PE + 1, OE );
	}
	
	public String getBareObject()
	{
		return triple.substring( PE + 2, OE - 1 );
	}
	
	public String getObjectContent()
	{
		String str = getObject();
		return URICenter.zhishiDecode( str.substring( str.lastIndexOf( "/" )+1, str.indexOf( ">" ) ) );
	}
	
	public String getIRIObjectContent()
	{
		String str = getObject();
		return TextTools.UnicodeToString( str.substring( str.lastIndexOf( "/" )+1, str.indexOf( ">" ) ) );
	}
	
	public String getObjectValue()
	{
		String str = getObject();
		return getValue( str );
	}
	
	public static String getValue( String str )
	{
		if( str.contains( "\"^^<" ) )
			return TextTools.UnicodeToString( str.substring( 1, str.indexOf( "\"^^<" ) ) );
		else if( str.contains( "\"@" ) )
			return TextTools.UnicodeToString( str.substring( 1, str.lastIndexOf( "\"@" ) ) );
		else
			return TextTools.UnicodeToString( str.substring( 1, str.length() - 1 ) );
	}
	
	public String getDataType()
	{
		String str = getObject();
		return str.substring( str.indexOf( "\"^^<" ) + 4, str.length()-1 );
	}
	
	public boolean objectIsLiteral()
	{
		return getObject().startsWith( "\"" );
	}
	
	public boolean objectIsTypedData()
	{
		return objectIsLiteral() && getObject().contains( "\"^^<" );
	}
	
	public boolean objectIsURIRef()
	{
		return getObject().startsWith( "<" );
	}
	
	public static void main(String args[])
	{
		TripleReader tr = new TripleReader( "<http://zhishi.me//resource/%E3%80%8A%E4%B8%AD> <http://www.w3.org/2000/01/rdf-schema#label> \"\u300A\u4E2D\"^^<http://www.w3.org/2001/XMLSchema#double> ." );
		System.out.println( tr.getSubject() );
		System.out.println( tr.getBarePredicate() );
		System.out.println( tr.getObjectValue() );
		System.out.println( tr.getDataType() );
	}
}
