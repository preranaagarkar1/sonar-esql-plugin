package com.exxeta.iss.sonar.esql.tree.impl.expression;

import java.util.Iterator;
import java.util.List;

import com.exxeta.iss.sonar.esql.api.symbols.Symbol;
import com.exxeta.iss.sonar.esql.api.symbols.Type;
import com.exxeta.iss.sonar.esql.api.symbols.TypeSet;
import com.exxeta.iss.sonar.esql.api.tree.Tree;
import com.exxeta.iss.sonar.esql.api.tree.expression.IdentifierTree;
import com.exxeta.iss.sonar.esql.api.tree.lexical.SyntaxToken;
import com.exxeta.iss.sonar.esql.api.tree.symbols.Scope;
import com.exxeta.iss.sonar.esql.api.tree.symbols.type.TypableTree;
import com.exxeta.iss.sonar.esql.api.visitors.DoubleDispatchVisitor;
import com.exxeta.iss.sonar.esql.tree.impl.EsqlTree;
import com.exxeta.iss.sonar.esql.tree.impl.lexical.InternalSyntaxToken;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterators;

public class IdentifierTreeImpl extends EsqlTree implements IdentifierTree, TypableTree {

	  private final InternalSyntaxToken nameToken;
	  private final Kind kind;
	  private Symbol symbol = null;
	  private TypeSet types = TypeSet.emptyTypeSet();
	  private Scope scope;

	  public IdentifierTreeImpl(Kind kind, InternalSyntaxToken nameToken) {
	    this.kind = kind;
	    this.nameToken = Preconditions.checkNotNull(nameToken);
	  }

	  @Override
	  public Kind getKind() {
	    return kind;
	  }

	  @Override
	  public SyntaxToken identifierToken() {
	    return nameToken;
	  }

	  @Override
	  public String name() {
	    return identifierToken().text();
	  }

	  @Override
	  public String toString() {
	    return name();
	  }

	  @Override
	  public Symbol symbol() {
	    return symbol;
	  }

	  public void setSymbol(Symbol symbol) {
	    this.symbol = symbol;
	  }

	  @Override
	  public TypeSet types() {
	    if (symbol == null) {
	      return types.immutableCopy();
	    } else {
	      return symbol.types();
	    }
	  }

	  @Override
	  public void add(Type type) {
	    if (symbol == null) {
	      types.add(type);
	    } else {
	      symbol.addType(type);
	    }
	  }

	  @Override
	  public Iterator<Tree> childrenIterator() {
	    return Iterators.<Tree>singletonIterator(nameToken);
	  }

	  @Override
	  public void accept(DoubleDispatchVisitor visitor) {
	    visitor.visitIdentifier(this);
	  }

	  @Override
	  public Scope scope(){
	    return scope;
	  }

	  public void scope(Scope scope) {
	    this.scope = scope;
	  }

	  @Override
	  public List<IdentifierTree> bindingIdentifiers() {
	    return ImmutableList.of((IdentifierTree) this);
	  }
	}