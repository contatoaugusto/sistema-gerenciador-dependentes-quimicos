package controleacesso.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import controleacesso.web.dao.RecursoDao;
import controleacesso.web.modelo.Recurso;


@FacesConverter(value = "recursoSelectOneMenuConverter")
public class RecursoSelectOneMenuConverter implements Converter {

	private static final Log log = LogFactory
			.getLog(RecursoSelectOneMenuConverter.class);

	RecursoDao recursoDAO;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		if (value == null || value.toString().trim().equals("")
				|| value.toString().trim().equals("0"))
			return null;

		for (char c : value.toCharArray()) {
			if (!Character.isDigit(c))
				return null;
		}
		return recursoDAO.getObjDao(Integer.parseInt(value));
		// If u look below, I convert the object into a unique string, which is
		// its id.
		// Therefore, I just need to write a method that query the object back
		// from the
		// database if given a id. getProjectById, is a method inside my Session
		// Bean that
		// does what I just described
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		if (value == null || value.toString().trim().equals(""))
			return null;
		if (value instanceof Recurso)
			return String.valueOf(((Recurso) value).getIdRecurso()); // -->
																		// convert
																		// to a
																		// unique
																		// string.

		return String.valueOf(value);
	}
}
