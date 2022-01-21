package INF2120;

public class DescriptionLatex {
    public static final String FICHIER_DEBUT =
            """
            \\documentclass[landscape]{article}
            \\usepackage[T1]{fontenc}
            \\usepackage[utf8]{inputenc}

            \\usepackage{tikz}

            \\usetikzlibrary{shapes.multipart, shapes.geometric}

            \\tikzset{
                    edge from parent path={(\\tikzparentnode.south) -| (\\tikzchildnode.north)},
                    every one node part/.style={font=\\bfseries},
                    classeNode/.style = {
                            draw,
                            rectangle split,
                            rectangle split parts=3,
                            align=left
                    },
                    abstract/.style = {
                            font=\\itshape
                    },
                    extends/.style = {draw, regular polygon, regular polygon sides=3}
            }

            \\newcommand{\\abstrait}[1]{\\textit{#1}}

            \\begin{document}
            """;

    public static final String FICHIER_FIN =
            """
            \\end{document}
            """;

    public static final String PAGE_DEBUT =
            """
            \\centering{
            \\begin{figure}
                \\begin{tikzpicture}
                \\path
            """;

    public static final String PAGE_FIN =
            """
                ;
                \\end{tikzpicture}
            \\end{figure}
            }

            \\clearpage    
            """;

    public static final String CLASSE_INTERNE_PREFIX =
            """
            child{
            """;

    public static final String CLASSE_DEBUT =
            """
            node[classeNode]
                {     
                    \\nodepart{one}
            """;

    public static final String CLASSE_FIN =
            """
            }
            """;

    public static final String CLASSE_INTERNE_SUFFIX =
            """
            }  
            """;

    public static final String LISTE_CLASSE_DEBUT =
            """
            [level distance=20mm]
            child{
                node[extends] {}
                [level distance=20mm, sibling distance=70mm]
            """;

    public static final String LISTE_CLASSE_FIN =
            """
            }
            """;

    public static final String LISTE_ATTRIBUT_DEBUT =
            """
            \\nodepart{two}
            """;

    public static final String LISTE_ATTRIBUT_SEP =
            """
            \\\\
            """;

    public static final String LISTE_METHODE_DEBUT =
            """
            \\nodepart{three}   
            """;

    public static final String LISTE_METHODE_SEP =
            """
            \\\\
            """;

    public static final String ABSTRAIT_DEBUT =
            """
            \\abstrait{
            """;

    public static final String ABSTRAIT_FIN =
            """
            }
            """;

    public static final String PARAMETRE_DEBUT = "(";
    public static final String PARAMETRE_SEP = ", ";
    public static final String PARAMETRE_FIN = ")";
}