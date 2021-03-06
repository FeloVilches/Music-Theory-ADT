package music;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;


@RunWith(Enclosed.class)
public class NoteTest {
	
	
	public static class WithoutParameters {
	

		@Test
		public final void testConstructor() {		
			Note c = new Note(Note.C, 0);
			Note d = new Note(Note.D, 1);
			Note e = new Note(Note.E, 2);
			Note f = new Note(Note.F, 3);
			Note g = new Note(Note.G, 4);
			Note a = new Note(Note.A, 5);		
			Note b = new Note(Note.B, 6);
			assertEquals(c.toString(), "C0");
			assertEquals(d.toString(), "D1");
			assertEquals(e.toString(), "E2");
			assertEquals(f.toString(), "F3");
			assertEquals(g.toString(), "G4");
			assertEquals(a.toString(), "A5");
			assertEquals(b.toString(), "B6");
		}
		
		@Test
		public final void testConstructorString() {
			
			assertEquals(new Note("C0").toString(), "C0");
			assertEquals(new Note("D1").toString(), "D1");
			assertEquals(new Note("E2").toString(), "E2");
			assertEquals(new Note("F3").toString(), "F3");
			assertEquals(new Note("G4").toString(), "G4");
			assertEquals(new Note("A5").toString(), "A5");
			assertEquals(new Note("B6").toString(), "B6");
			
			assertEquals(new Note("C#0").toString(), "C#/Db0");
			assertEquals(new Note("Db1").toString(), "C#/Db1");
			assertEquals(new Note("Eb2").toString(), "D#/Eb2");
			assertEquals(new Note("F#3").toString(), "F#/Gb3");
			assertEquals(new Note("G#4").toString(), "G#/Ab4");
			assertEquals(new Note("Ab5").toString(), "G#/Ab5");
			assertEquals(new Note("Bb6").toString(), "A#/Bb6");
			
			assertEquals(new Note("C").toString(), "C4");
			assertEquals(new Note("d").toString(), "D4");
			assertEquals(new Note("E").toString(), "E4");
			assertEquals(new Note("f").toString(), "F4");
			assertEquals(new Note("G").toString(), "G4");
			assertEquals(new Note("a").toString(), "A4");
			assertEquals(new Note("B").toString(), "B4");
			
			assertEquals(new Note("C#").toString(), "C#/Db4");
			assertEquals(new Note("Db").toString(), "C#/Db4");
			assertEquals(new Note("Eb").toString(), "D#/Eb4");
			assertEquals(new Note("f#").toString(), "F#/Gb4");
			assertEquals(new Note("G#").toString(), "G#/Ab4");
			assertEquals(new Note("ab").toString(), "G#/Ab4");
			assertEquals(new Note("bb").toString(), "A#/Bb4");
		}
	
			
		
		@Test
		public final void testConstructorSharpFlat() {		
			Note c = new Note(Note.Csharp, 0);
			Note d = new Note(Note.Db, 1);
			Note e = new Note(Note.Eb, 2);
			Note f = new Note(Note.Fsharp, 3);
			Note g = new Note(Note.Gb, 4);
			Note a = new Note(Note.Asharp, 5);		
			Note b = new Note(Note.Bb, 6);
			assertEquals(c.toString(), "C#/Db0");
			assertEquals(d.toString(), "C#/Db1");
			assertEquals(e.toString(), "D#/Eb2");
			assertEquals(f.toString(), "F#/Gb3");
			assertEquals(g.toString(), "F#/Gb4");
			assertEquals(a.toString(), "A#/Bb5");
			assertEquals(b.toString(), "A#/Bb6");
		}
		
		
		@Test(expected=IllegalArgumentException.class)
		public final void testConstructorIllegal1(){		
			new Note(Note.C-1, 0);		
		}
		
		@Test(expected=IllegalArgumentException.class)
		public final void testConstructorIllegal2(){		
			new Note(Note.B+1, 0);		
		}
		
		@Test(expected=IllegalArgumentException.class)
		public final void testConstructorIllegal3(){		
			new Note(Note.C, Note.MAX_OCTAVE + 1);
		}
		
		@Test(expected=IllegalArgumentException.class)
		public final void testConstructorIllegal4(){		
			new Note(Note.C, -1);		
		}
		
			
		
		@Test
		public final void testConstructorLegal(){
			new Note(Note.C, Note.MIN_OCTAVE);
			new Note(Note.C, Note.MAX_OCTAVE);
			new Note(Note.B, Note.MAX_OCTAVE);
		}
		
		
		@Test
		public final void testTranspose(){		
			String[] notes = {"C", "D", "E3", "F", "G4", "A", "B7", "C#1", "D2", "Db", "D#5", "Eb5", "E8", "F", "F#1", "A5", "G", "B", "Bb8"};
			
			for(int i=0; i<notes.length; i++){
				for(int j=-Interval.OCTAVE.getValue(); j<Interval.OCTAVE.getValue(); j++){				
					Note n1 = new Note(notes[i]);
					Note n2 = n1.findNoteByInterval(j);
					n1.transpose(j);
					assertEquals(n1, n2);			
				}
			}		
		}
		
		@Test
		public final void testTranspose2(){
			
			int minNote = Note.C;
			int maxNote = Note.B;
			int minOct = Note.MIN_OCTAVE;
			int maxOct = Note.MAX_OCTAVE;		
			
			for(int i=minNote; i<=maxNote; i++){						
				for(int j=minOct+1; j<maxOct; j++){
					Note n1 = new Note(i, j);
					for(int k=-Interval.OCTAVE.getValue(); k<Interval.OCTAVE.getValue(); k++){					
						Note n2 = n1.findNoteByInterval(k);
						n1.transpose(k);
						assertEquals(n1, n2);
						n1.transpose(-k);
					}				
				}		
			}	
		}
		
		@Test
		public final void testTransposeLegal1(){
			
			boolean error = false;
			
			Note n = new Note(Note.D, Note.MIN_OCTAVE);
			n.transpose(-1);
			n.transpose(-1);
			try{
				n.transpose(-1);
			} catch(IllegalArgumentException e){
				error = true;
			}
			assertTrue(error);		
		}
		
		@Test
		public final void testTransposeLegal2(){		
			boolean error = false;		
			Note n = new Note(Note.A, Note.MAX_OCTAVE);
			n.transpose(1);
			n.transpose(1);
			try{
				n.transpose(1);
			} catch(IllegalArgumentException e){
				error = true;
			}
			assertTrue(error);		
		}
		
		@Test
		public final void testTransposeManuallyAsc(){
			
			Note a = new Note("C");
			
			a.transpose(2);
			assertEquals(a, new Note("D"));
			
			a.transpose(2);
			assertEquals(a, new Note("E"));
			
			a.transpose(2);
			assertEquals(a, new Note("f#"));
			
			a.transpose(2);
			assertEquals(a, new Note("ab"));
			
			a.transpose(1);
			assertEquals(a, new Note("a"));
			
			a.transpose(3);
			assertEquals(a, new Note("c5"));
			
			a.transpose(3);
			assertEquals(a, new Note("eb5"));
			
			a.transpose(12);
			assertEquals(a, new Note("eb6"));		
			
		}
		
		@Test
		public final void testTransposeManuallyDesc(){
			
			Note a = new Note("C");
			
			a.transpose(-2);
			assertEquals(a, new Note("Bb3"));
			
			a.transpose(-2);
			assertEquals(a, new Note("Ab3"));
			
			a.transpose(-2);
			assertEquals(a, new Note("f#3"));
			
			a.transpose(-2);
			assertEquals(a, new Note("e3"));
			
			a.transpose(-1);
			assertEquals(a, new Note("d#3"));
			
			a.transpose(-3);
			assertEquals(a, new Note(Note.C, 3));
			
			a.transpose(-3);
			assertEquals(a, new Note(Note.A, 2));
			
			a.transpose(-12);
			assertEquals(a, new Note(Note.A, 1));		
			
		}
		
		
		@Test(expected=IllegalArgumentException.class)
		public final void testTransposeIllegal1(){
			new Note(Note.C, 4).transpose(-5000);
		}
		
		@Test(expected=IllegalArgumentException.class)
		public final void testTransposeIllegal2(){		
			new Note(Note.C, 4).transpose(5000);
		}
		
		@Test(expected=IllegalArgumentException.class)
		public final void testTransposeIllegal3(){		
			new Note(Note.C, 0).transpose(-1);
		}
		
		@Test(expected=IllegalArgumentException.class)
		public final void testTransposeIllegal4(){		
			new Note(Note.B, Note.MAX_OCTAVE).transpose(1);
		}
	
		
	}
	
	
	@RunWith(Parameterized.class)
	public static class ConstructorStringIllegal {

		@Parameters
		public static Collection<Object> strings() {
			return Arrays.asList(new Object[] { "WRONG FORMAT", "CX", "C156568", "U#", "A%5", "  A#6  ", "Xb3", "Cl3", "DB3", "", " ", "\n", "N", "aa", "#b"});
		}

		@Parameter
		public String str;

		@Test(expected = IllegalArgumentException.class)
		public final void testConstructorStringIllegal() {
			new Note(str);
		}
	}
	
	
	
	
}
