-module(vodkatv_channels).

-include_lib("eqc/include/eqc.hrl").
-include("measure.hrl").

-compile(export_all).

-define(MAX_CHANNELS, 1000).

grow({NumChannels, _NumEPGChannels}) ->
  NumChannels1 = NumChannels + 10,
  [{NumChannels1, 0},
   {NumChannels1, (random:uniform(?MAX_CHANNELS + 1)-1)},
   {NumChannels1, NumChannels1}].

eval_cmds({NumChannels, NumEPGChannels}) ->
    SetupCommands = get_setup_commands(NumEPGChannels),
    RunCommands = get_run_commands(NumChannels),
    TearDownCommands = get_teardown_commands(),
    measure_java:run_java_commands(false, 5, SetupCommands,
        lists:flatten(RunCommands), TearDownCommands).

measure_size({NumChannels, _NumEPGChannels}) ->
    NumChannels.

get_java_code(Commands) ->
    ["{",
        "try {"
            "VoDKATVChannels v = VoDKATVChannels.getInstance();",
            Commands,
        "} catch(Exception e) {",
            "e.printStackTrace();",
        "}",
        "return null;",
    "}"].

get_run_commands(NumChannels) ->
    Commands = get_java_code([
        "v.findChannelsInformation(" ++ integer_to_list(NumChannels) ++ ");"
    ]),
    lists:flatten(Commands).

get_setup_commands(NumEPGChannels) ->
    Commands = get_java_code([
        "v.setUp(" ++ integer_to_list(NumEPGChannels) ++ ");"
    ]),
    lists:flatten(Commands).

get_teardown_commands() ->
    Commands = get_java_code([
        "v.tearDown();"
    ]),
    lists:flatten(Commands).

global_setup() ->
    ok.

global_teardown() ->
    ok.

measure() ->
    java:set_timeout(infinity),
    ClassPaths = ["../examples/vodkatv/src/", "../examples/vodkatv/vodkatv/"] ++
            filelib:wildcard("../examples/vodkatv/lib/*.jar"),
    Family = #family{initial = {0,0}, grow = fun grow/1},
    Axes = #axes{size = fun measure_size/1,
                time = fun eval_cmds/1,
                repeat = 5},
    {Time, _} = timer:tc(measure_java, measure_java,
        [1,  ?MAX_CHANNELS, Family, Axes, ClassPaths,
        fun global_setup/0, fun global_teardown/0]),
    Time / 1000000.
