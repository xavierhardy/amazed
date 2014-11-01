/*
The MIT License (MIT)

Copyright (c) 2014 Xavier Hardy

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package amazed.maze;

public class Case extends CaseAbstraite {
	private boolean nord = false;
	private boolean sud = false;
	private boolean est = false;
	private boolean ouest = false;
	
	public Case(boolean nord, boolean sud, boolean est, boolean ouest){
		this.nord = nord;
		this.sud = sud;
		this.est = est;
		this.ouest = ouest;
	}
	
	public Case(){}
	
	public Case(boolean full){
		nord = full;
		sud = full;
		est = full;
		ouest = full;
		dansLaby = !full;
	}
	
	public boolean getNord(){
		return nord;
	}

	public boolean getSud(){
		return sud;
	}
	
	public boolean getEst(){
		return est;
	}
	
	public boolean getOuest(){
		return ouest;
	}
	
	public void setNord(boolean full){
		nord = full;
	}

	public void setSud(boolean full){
		sud = full;
	}
	
	public void setEst(boolean full){
		est = full;
	}
	
	public void setOuest(boolean full){
		ouest = full;
	}
	
	public Case clone() {
		Case caseClone = new Case(nord,sud,est,ouest);
		return caseClone;
	}
}
