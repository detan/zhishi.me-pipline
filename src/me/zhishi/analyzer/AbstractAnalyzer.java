package me.zhishi.analyzer;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.zhishi.tools.URICenter;
import me.zhishi.tools.file.TripleReader;

public class AbstractAnalyzer extends DataAnalyzer
{
	public static void main( String[] args )
	{
		AbstractAnalyzer ana = new AbstractAnalyzer( 3.0, URICenter.source_name_hudong );
		ana.driver();
		ana.closeReader();
	}
	
	public AbstractAnalyzer( double version, String source )
	{
		super( version, source, "abstract" );
	}
	
	public void driver()
	{
		while( fileReader.readNextLine() != null )
		{
			TripleReader tr = fileReader.getTripleReader();
			String label = tr.getSubjectContent();
			ArrayList<String> absArray = extractDefinitionSentences( label, tr.getObjectValue() );
			for( int i = 0; i < absArray.size(); ++i )
				System.out.println( label + "\t" + absArray.get( i ) );
		}
	}
	
	public ArrayList<String> extractDefinitionSentences( String label, String abs )
	{
		if( fileName.getSource().equals( URICenter.source_name_zhwiki ) )
			label = label.replaceAll( " \\(.*?\\)$", "" );
		else
			label = label.replaceAll( "\\[.*?\\]$", "" );
		
		String noSense = "[\\s，：]";
		
		Pattern pattern;
		Matcher matcher;
		ArrayList<String> absArray = new ArrayList<String>();
		String[] absegs = abs.split( "。" );
		
		pattern = Pattern.compile( "^(.*?" + Pattern.quote(label) + "(（.*?）)?" + noSense + "?(，?(又称|又叫|也叫|简称).*?，)?|这|他|她|它)(指的是|是指|是)(.*)" );
		matcher = pattern.matcher( absegs[0] );
		if( matcher.find() )
		{
//			absArray.add( matcher.group( 6 ) );
		}
		pattern = Pattern.compile( "^(" + Pattern.quote(label) + "(（.*?）)?.*)，(指的是|是指|是)(.*)" );
		matcher = pattern.matcher( absegs[0] );
		if( absArray.size() == 0 && matcher.find() )
		{
//			absArray.add( matcher.group( 4 ) );
		}
		pattern = Pattern.compile( "^(" + Pattern.quote(label) + "(（.*?）)?)，(.*)" );
		matcher = pattern.matcher( absegs[0] );
		if( absArray.size() == 0 && matcher.find() )
		{
			absArray.add( matcher.group( 3 ) );
		}
		
		if( absArray.size() == 0 )
		{
			
		}
			
		return absArray;
	}
}
